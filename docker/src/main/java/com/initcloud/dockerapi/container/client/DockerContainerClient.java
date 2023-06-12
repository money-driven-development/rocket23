package com.initcloud.dockerapi.container.client;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.AttachContainerCmd;
import com.github.dockerjava.api.command.AuthCmd;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.CommitCmd;
import com.github.dockerjava.api.command.ConnectToNetworkCmd;
import com.github.dockerjava.api.command.ContainerDiffCmd;
import com.github.dockerjava.api.command.CopyArchiveFromContainerCmd;
import com.github.dockerjava.api.command.CopyArchiveToContainerCmd;
import com.github.dockerjava.api.command.CopyFileFromContainerCmd;
import com.github.dockerjava.api.command.CreateConfigCmd;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateImageCmd;
import com.github.dockerjava.api.command.CreateNetworkCmd;
import com.github.dockerjava.api.command.CreateSecretCmd;
import com.github.dockerjava.api.command.CreateServiceCmd;
import com.github.dockerjava.api.command.CreateVolumeCmd;
import com.github.dockerjava.api.command.DisconnectFromNetworkCmd;
import com.github.dockerjava.api.command.EventsCmd;
import com.github.dockerjava.api.command.ExecCreateCmd;
import com.github.dockerjava.api.command.ExecStartCmd;
import com.github.dockerjava.api.command.InfoCmd;
import com.github.dockerjava.api.command.InitializeSwarmCmd;
import com.github.dockerjava.api.command.InspectConfigCmd;
import com.github.dockerjava.api.command.InspectContainerCmd;
import com.github.dockerjava.api.command.InspectExecCmd;
import com.github.dockerjava.api.command.InspectImageCmd;
import com.github.dockerjava.api.command.InspectNetworkCmd;
import com.github.dockerjava.api.command.InspectServiceCmd;
import com.github.dockerjava.api.command.InspectSwarmCmd;
import com.github.dockerjava.api.command.InspectVolumeCmd;
import com.github.dockerjava.api.command.JoinSwarmCmd;
import com.github.dockerjava.api.command.KillContainerCmd;
import com.github.dockerjava.api.command.LeaveSwarmCmd;
import com.github.dockerjava.api.command.ListConfigsCmd;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.command.ListNetworksCmd;
import com.github.dockerjava.api.command.ListSecretsCmd;
import com.github.dockerjava.api.command.ListServicesCmd;
import com.github.dockerjava.api.command.ListSwarmNodesCmd;
import com.github.dockerjava.api.command.ListTasksCmd;
import com.github.dockerjava.api.command.ListVolumesCmd;
import com.github.dockerjava.api.command.LoadImageAsyncCmd;
import com.github.dockerjava.api.command.LoadImageCmd;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.command.LogSwarmObjectCmd;
import com.github.dockerjava.api.command.PauseContainerCmd;
import com.github.dockerjava.api.command.PingCmd;
import com.github.dockerjava.api.command.PruneCmd;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.command.PushImageCmd;
import com.github.dockerjava.api.command.RemoveConfigCmd;
import com.github.dockerjava.api.command.RemoveContainerCmd;
import com.github.dockerjava.api.command.RemoveImageCmd;
import com.github.dockerjava.api.command.RemoveNetworkCmd;
import com.github.dockerjava.api.command.RemoveSecretCmd;
import com.github.dockerjava.api.command.RemoveServiceCmd;
import com.github.dockerjava.api.command.RemoveSwarmNodeCmd;
import com.github.dockerjava.api.command.RemoveVolumeCmd;
import com.github.dockerjava.api.command.RenameContainerCmd;
import com.github.dockerjava.api.command.ResizeContainerCmd;
import com.github.dockerjava.api.command.ResizeExecCmd;
import com.github.dockerjava.api.command.RestartContainerCmd;
import com.github.dockerjava.api.command.SaveImageCmd;
import com.github.dockerjava.api.command.SaveImagesCmd;
import com.github.dockerjava.api.command.SearchImagesCmd;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.command.StopContainerCmd;
import com.github.dockerjava.api.command.TagImageCmd;
import com.github.dockerjava.api.command.TopContainerCmd;
import com.github.dockerjava.api.command.UnpauseContainerCmd;
import com.github.dockerjava.api.command.UpdateContainerCmd;
import com.github.dockerjava.api.command.UpdateServiceCmd;
import com.github.dockerjava.api.command.UpdateSwarmCmd;
import com.github.dockerjava.api.command.UpdateSwarmNodeCmd;
import com.github.dockerjava.api.command.VersionCmd;
import com.github.dockerjava.api.command.WaitContainerCmd;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.AuthConfig;
import com.github.dockerjava.api.model.Identifier;
import com.github.dockerjava.api.model.PruneType;
import com.github.dockerjava.api.model.SecretSpec;
import com.github.dockerjava.api.model.ServiceSpec;
import com.github.dockerjava.api.model.SwarmSpec;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;

import lombok.Getter;

@Component
public class DockerContainerClient implements Closeable, DockerClient {

	@Getter
	private DockerClientConfig dockerClientConfig;
	private DockerHttpClient dockerHttpClient;

	public DockerContainerClient() {
		this.dockerClientConfig = DockerClientConfigBuilder.buildDefaultDockerClientConfig();
		this.dockerHttpClient = DockerContainerHttpClient.createDockerHttpClient();
	}

	public DockerClient getDockerClient() {
		return DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
	}

	@Override
	public AuthConfig authConfig() throws DockerException {
		return null;
	}

	@Override
	public AuthCmd authCmd() {
		return null;
	}

	@Override
	public InfoCmd infoCmd() {
		return null;
	}

	@Override
	public PingCmd pingCmd() {
		return null;
	}

	@Override
	public VersionCmd versionCmd() {
		return null;
	}

	@Override
	public PullImageCmd pullImageCmd(String repository) {
		return null;
	}

	@Override
	public PushImageCmd pushImageCmd(String name) {
		return null;
	}

	@Override
	public PushImageCmd pushImageCmd(Identifier identifier) {
		return null;
	}

	@Override
	public CreateImageCmd createImageCmd(String repository, InputStream imageStream) {
		return null;
	}

	@Override
	public LoadImageCmd loadImageCmd(InputStream imageStream) {
		return null;
	}

	@Override
	public LoadImageAsyncCmd loadImageAsyncCmd(InputStream imageStream) {
		return null;
	}

	@Override
	public SearchImagesCmd searchImagesCmd(String term) {
		return null;
	}

	@Override
	public RemoveImageCmd removeImageCmd(String imageId) {
		return null;
	}

	@Override
	public ListImagesCmd listImagesCmd() {
		return null;
	}

	@Override
	public InspectImageCmd inspectImageCmd(String imageId) {
		return null;
	}

	@Override
	public SaveImageCmd saveImageCmd(String name) {
		return null;
	}

	@Override
	public SaveImagesCmd saveImagesCmd() {
		return null;
	}

	@Override
	public ListContainersCmd listContainersCmd() {
		return null;
	}

	@Override
	public CreateContainerCmd createContainerCmd(String image) {
		return null;
	}

	@Override
	public StartContainerCmd startContainerCmd(String containerId) {
		return null;
	}

	@Override
	public ExecCreateCmd execCreateCmd(String containerId) {
		return null;
	}

	@Override
	public ResizeExecCmd resizeExecCmd(String execId) {
		return null;
	}

	@Override
	public InspectContainerCmd inspectContainerCmd(String containerId) {
		return null;
	}

	@Override
	public RemoveContainerCmd removeContainerCmd(String containerId) {
		return null;
	}

	@Override
	public WaitContainerCmd waitContainerCmd(String containerId) {
		return null;
	}

	@Override
	public AttachContainerCmd attachContainerCmd(String containerId) {
		return null;
	}

	@Override
	public ExecStartCmd execStartCmd(String execId) {
		return null;
	}

	@Override
	public InspectExecCmd inspectExecCmd(String execId) {
		return null;
	}

	@Override
	public LogContainerCmd logContainerCmd(String containerId) {
		return null;
	}

	@Override
	public CopyArchiveFromContainerCmd copyArchiveFromContainerCmd(String containerId, String resource) {
		return null;
	}

	@Override
	public CopyFileFromContainerCmd copyFileFromContainerCmd(String containerId, String resource) {
		return null;
	}

	@Override
	public CopyArchiveToContainerCmd copyArchiveToContainerCmd(String containerId) {
		return null;
	}

	@Override
	public ContainerDiffCmd containerDiffCmd(String containerId) {
		return null;
	}

	@Override
	public StopContainerCmd stopContainerCmd(String containerId) {
		return null;
	}

	@Override
	public KillContainerCmd killContainerCmd(String containerId) {
		return null;
	}

	@Override
	public UpdateContainerCmd updateContainerCmd(String containerId) {
		return null;
	}

	@Override
	public RenameContainerCmd renameContainerCmd(String containerId) {
		return null;
	}

	@Override
	public RestartContainerCmd restartContainerCmd(String containerId) {
		return null;
	}

	@Override
	public ResizeContainerCmd resizeContainerCmd(String containerId) {
		return null;
	}

	@Override
	public CommitCmd commitCmd(String containerId) {
		return null;
	}

	@Override
	public BuildImageCmd buildImageCmd() {
		return null;
	}

	@Override
	public BuildImageCmd buildImageCmd(File dockerFileOrFolder) {
		return null;
	}

	@Override
	public BuildImageCmd buildImageCmd(InputStream tarInputStream) {
		return null;
	}

	@Override
	public TopContainerCmd topContainerCmd(String containerId) {
		return null;
	}

	@Override
	public TagImageCmd tagImageCmd(String imageId, String imageNameWithRepository, String tag) {
		return null;
	}

	@Override
	public PauseContainerCmd pauseContainerCmd(String containerId) {
		return null;
	}

	@Override
	public UnpauseContainerCmd unpauseContainerCmd(String containerId) {
		return null;
	}

	@Override
	public EventsCmd eventsCmd() {
		return null;
	}

	@Override
	public StatsCmd statsCmd(String containerId) {
		return null;
	}

	@Override
	public CreateVolumeCmd createVolumeCmd() {
		return null;
	}

	@Override
	public InspectVolumeCmd inspectVolumeCmd(String name) {
		return null;
	}

	@Override
	public RemoveVolumeCmd removeVolumeCmd(String name) {
		return null;
	}

	@Override
	public ListVolumesCmd listVolumesCmd() {
		return null;
	}

	@Override
	public ListNetworksCmd listNetworksCmd() {
		return null;
	}

	@Override
	public InspectNetworkCmd inspectNetworkCmd() {
		return null;
	}

	@Override
	public CreateNetworkCmd createNetworkCmd() {
		return null;
	}

	@Override
	public RemoveNetworkCmd removeNetworkCmd(String networkId) {
		return null;
	}

	@Override
	public ConnectToNetworkCmd connectToNetworkCmd() {
		return null;
	}

	@Override
	public DisconnectFromNetworkCmd disconnectFromNetworkCmd() {
		return null;
	}

	@Override
	public InitializeSwarmCmd initializeSwarmCmd(SwarmSpec swarmSpec) {
		return null;
	}

	@Override
	public InspectSwarmCmd inspectSwarmCmd() {
		return null;
	}

	@Override
	public JoinSwarmCmd joinSwarmCmd() {
		return null;
	}

	@Override
	public LeaveSwarmCmd leaveSwarmCmd() {
		return null;
	}

	@Override
	public UpdateSwarmCmd updateSwarmCmd(SwarmSpec swarmSpec) {
		return null;
	}

	@Override
	public UpdateSwarmNodeCmd updateSwarmNodeCmd() {
		return null;
	}

	@Override
	public RemoveSwarmNodeCmd removeSwarmNodeCmd(String swarmNodeId) {
		return null;
	}

	@Override
	public ListSwarmNodesCmd listSwarmNodesCmd() {
		return null;
	}

	@Override
	public ListServicesCmd listServicesCmd() {
		return null;
	}

	@Override
	public CreateServiceCmd createServiceCmd(ServiceSpec serviceSpec) {
		return null;
	}

	@Override
	public InspectServiceCmd inspectServiceCmd(String serviceId) {
		return null;
	}

	@Override
	public UpdateServiceCmd updateServiceCmd(String serviceId, ServiceSpec serviceSpec) {
		return null;
	}

	@Override
	public RemoveServiceCmd removeServiceCmd(String serviceId) {
		return null;
	}

	@Override
	public ListTasksCmd listTasksCmd() {
		return null;
	}

	@Override
	public LogSwarmObjectCmd logServiceCmd(String serviceId) {
		return null;
	}

	@Override
	public LogSwarmObjectCmd logTaskCmd(String taskId) {
		return null;
	}

	@Override
	public PruneCmd pruneCmd(PruneType pruneType) {
		return null;
	}

	@Override
	public ListSecretsCmd listSecretsCmd() {
		return null;
	}

	@Override
	public CreateSecretCmd createSecretCmd(SecretSpec secretSpec) {
		return null;
	}

	@Override
	public RemoveSecretCmd removeSecretCmd(String secretId) {
		return null;
	}

	@Override
	public ListConfigsCmd listConfigsCmd() {
		return null;
	}

	@Override
	public CreateConfigCmd createConfigCmd() {
		return null;
	}

	@Override
	public InspectConfigCmd inspectConfigCmd(String configId) {
		return null;
	}

	@Override
	public RemoveConfigCmd removeConfigCmd(String configId) {
		return null;
	}

	@Override
	public void close() throws IOException {

	}
}
