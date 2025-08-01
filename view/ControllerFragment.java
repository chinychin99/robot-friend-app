// Represents Controller Screen
package com.example.yourapp.controller;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.yourapp.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ControllerFragment extends Fragment {

    private static final String ESP32_AP_IP = "http://192.168.4.1";
    private TextView statusMsg;

    public ControllerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_controller, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        statusMsg = view.findViewById(R.id.controllerTitle); // Replace or add a new TextView if needed

        Button btnUp = view.findViewById(R.id.btnUp);
        Button btnDown = view.findViewById(R.id.btnDown);
        Button btnLeft = view.findViewById(R.id.btnLeft);
        Button btnRight = view.findViewById(R.id.btnRight);
        Button btnRise = view.findViewById(R.id.btnRise);
        Button btnFall = view.findViewById(R.id.btnFall);
        Button btnConnect = view.findViewById(R.id.btnConnect); // optional

        btnUp.setOnClickListener(v -> handleMove("up"));
        btnDown.setOnClickListener(v -> handleMove("down"));
        btnLeft.setOnClickListener(v -> handleMove("left"));
        btnRight.setOnClickListener(v -> handleMove("right"));
        btnRise.setOnClickListener(v -> handleMove("rise"));
        btnFall.setOnClickListener(v -> handleMove("fall"));
        btnConnect.setOnClickListener(v -> handleMove("connect")); // optional
    }

    private void handleMove(String direction) {
        statusMsg.setText("Sending " + direction + " command...");

        // Allow network on main thread (temporary for quick testing)
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        try {
            URL url = new URL(ESP32_AP_IP + "/move?dir=" + direction);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result = reader.readLine();
            reader.close();

            statusMsg.setText("Success: " + result);
        } catch (Exception e) {
            e.printStackTrace();
            statusMsg.setText("Error: Cannot reach robot.");
            Toast.makeText(getContext(), "Failed to send command to robot.", Toast.LENGTH_SHORT).show();
        }
    }
}

