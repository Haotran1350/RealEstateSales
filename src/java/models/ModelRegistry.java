/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author Hao
 */
public class ModelRegistry {
    private long modelId;
    private String modelVersion;
    private String modelType;     // VALUATION/LEAD_SCORING/RECO
    private String metricsJson;
    private String artifactPath;
    private Timestamp trainedAt;

    public ModelRegistry() {}

    public ModelRegistry(long modelId, String modelVersion, String modelType,
                         String metricsJson, String artifactPath, Timestamp trainedAt) {
        this.modelId = modelId;
        this.modelVersion = modelVersion;
        this.modelType = modelType;
        this.metricsJson = metricsJson;
        this.artifactPath = artifactPath;
        this.trainedAt = trainedAt;
    }

    public long getModelId() { return modelId; }
    public void setModelId(long modelId) { this.modelId = modelId; }

    public String getModelVersion() { return modelVersion; }
    public void setModelVersion(String modelVersion) { this.modelVersion = modelVersion; }

    public String getModelType() { return modelType; }
    public void setModelType(String modelType) { this.modelType = modelType; }

    public String getMetricsJson() { return metricsJson; }
    public void setMetricsJson(String metricsJson) { this.metricsJson = metricsJson; }

    public String getArtifactPath() { return artifactPath; }
    public void setArtifactPath(String artifactPath) { this.artifactPath = artifactPath; }

    public Timestamp getTrainedAt() { return trainedAt; }
    public void setTrainedAt(Timestamp trainedAt) { this.trainedAt = trainedAt; }
}
