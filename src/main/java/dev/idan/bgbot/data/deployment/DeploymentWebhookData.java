package dev.idan.bgbot.data.deployment;

import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.combined.data.BuildDeploymentData;

@JsonTypeName("deployment")
public class DeploymentWebhookData extends BuildDeploymentData {}
