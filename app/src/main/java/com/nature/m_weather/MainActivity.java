package com.nature.m_weather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText cityInput;
    Button getWeatherBtn;
    TextView resultText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityInput = findViewById(R.id.cityInput);
        getWeatherBtn = findViewById(R.id.getWeatherBtn);
        resultText = findViewById(R.id.resultText);

        // 1. Setup to Retrifit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final WeatherInterface weatherInterface = retrofit.create(WeatherInterface.class);

        getWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityInput.getText().toString().trim();

                if (city.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    // API Call
                    Call<WeatherResponse> call = weatherInterface.getWeatherData(city, "71225afc6bca30c9f864d993c9b2a549", "metric");

                    call.enqueue(new Callback<WeatherResponse>() {
                        @Override
                        public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {

                                double temp = response.body().getMain().getTemp();
                                resultText.setText(city + "-is Currently " + temp + "°C");
                            } else {
                                resultText.setText("Information not available. Please check Key.");
                            }
                        }

                        @Override
                        public void onFailure(Call<WeatherResponse> call, Throwable t) {
                            resultText.setText("connection Fail: " + t.getMessage());
                        }
                    });
                }
            }
        });
    }
}