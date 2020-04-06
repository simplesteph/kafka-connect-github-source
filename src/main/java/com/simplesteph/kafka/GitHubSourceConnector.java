package com.simplesteph.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simplesteph.kafka.utils.RepoJoinUtil;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitHubSourceConnector extends SourceConnector {
    private static Logger log = LoggerFactory.getLogger(GitHubSourceConnector.class);
    private GitHubSourceConnectorConfig config;
    private Map<String, String> settings;

    @Override
    public String version() {
        return VersionUtil.getVersion();
    }

    @Override
    public void start(Map<String, String> map) {
        config = new GitHubSourceConnectorConfig(map);
        settings=map;
    }

    @Override
    public Class<? extends Task> taskClass() {
        return GitHubSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int i) {
        // Define the individual task configurations that will be executed.
        String repos[]=config.getReposConfig();
        if(repos.length<i){
            log.info(String.format("Number of repositories i.e., %d to be followed are less than max tasks i.e., %d \n" +
                    "Therefore, setting max tasks to %d",repos.length,i,repos.length));
            i=repos.length;//setting max tasks to number repositories
        }

        ArrayList<Map<String, String>> configs = new ArrayList<>(i);

        //Distributing the repositories among the taskConfigs efficiently so that the possible max of repos per task is min
        int remainingRepos=repos.length;
        int currentRepo=0;
        while(remainingRepos>0){
            int NumOfReposForTask=(remainingRepos/i) +((remainingRepos%i==0)?0:1);
            String ReposForTask="";
            for(int j=0;j<NumOfReposForTask;j++){
                ReposForTask= RepoJoinUtil.Join(ReposForTask,repos[currentRepo+j]);
            }
            //Creating task setting/config
            Map<String, String> task_settings=new HashMap<>(settings);
            //updating repos for the task
            task_settings.put(GitHubSourceConnectorConfig.REPOS_CONFIG,ReposForTask);
            configs.add(task_settings);

            currentRepo+=NumOfReposForTask;
            remainingRepos-=NumOfReposForTask;
            i--;
        }

        return configs;
    }

    @Override
    public void stop() {
        // Do things that are necessary to stop your connector.
        // nothing is necessary to stop for this connector
    }

    @Override
    public ConfigDef config() {
        return GitHubSourceConnectorConfig.conf();
    }
}
