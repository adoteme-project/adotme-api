package com.example.adpotme_api.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Localizacao {
    private String latitude;
    private String longitude;

    public Localizacao(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters e setters
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Localização [Latitude=" + latitude + ", Longitude=" + longitude + "]";
    }

    // Método para calcular a distância entre esta localização e outra
    public int calcularDistancia(Double lat2, Double lon2) {
        // Converter as coordenadas de string para double
        double lat1 = Double.parseDouble(this.latitude);
        double lon1 = Double.parseDouble(this.longitude);
        double latitude2 = Double.parseDouble(String.valueOf(lat2));
        double longitude2 = Double.parseDouble(String.valueOf(lon2));

        // Raio da Terra em quilômetros
        final double RAIO_TERRA_KM = 6371.01;

        // Converter graus para radianos
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(latitude2);
        double lon2Rad = Math.toRadians(longitude2);

        // Fórmula de Haversine
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.pow(Math.sin(deltaLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcular a distância em km
        return (int) (RAIO_TERRA_KM * c);
    }

    public static Localizacao getLocalizacao() throws IOException {
        // Método original para obter a localização via IP
        URL ipCheckUrl = new URL("http://checkip.amazonaws.com/");
        String ip;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(ipCheckUrl.openStream()))) {
            ip = in.readLine();
            System.out.println("IP público: " + ip);
        }

        String token = "0fc758069f6498";  // Substitua pelo seu token
        URL url = new URL("https://ipinfo.io/" + ip + "/json?token=" + token);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (BufferedReader geoIn = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = geoIn.readLine()) != null) {
                response.append(inputLine);
            }
        }

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();

        if (jsonObject.has("loc")) {
            String loc = jsonObject.get("loc").getAsString();
            String[] latLong = loc.split(",");
            String latitude = latLong[0];
            String longitude = latLong[1];

            return new Localizacao(latitude, longitude);
        } else {
            throw new IOException("A chave 'loc' não foi encontrada na resposta.");
        }
    }
}
