package pl.put.poznan.sortingmadness.api.dto;

public class SortRequest {

    private String algorithm;
    private int[] data;

    public String getAlgorithm() {
        return algorithm;
    }

    public int[] getData() {
        return data;
    }
}