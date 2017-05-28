package git;


import domain.Config;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken on 1-4-2017.
 * Source open desktop: http://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
 */

public class Git {
    private GitHubClient client;
    private List contents;
    private List<GitRepository> repositorys;
    private List commits;
    private String mainRepository;
    private CommitService commitService;
    private ContentsService contentsService;
    private UserService userService;
    public boolean isLoggedIn;

    public Git() {
        client = new GitHubClient();
        userService = new UserService(client);
        repositorys = new ArrayList<GitRepository>();
        commits = new ArrayList<RepositoryCommit>();
        contents = new ArrayList<RepositoryContents>();
        isLoggedIn = false;
    }

    public GitHubClient getClient() {
        return client;
    }

    public List getContents() {
        return contents;
    }

    public List<GitRepository> getRepositorys() {
        return repositorys;
    }

    public List getCommits() {
        return commits;
    }

    public boolean login() throws IOException {
        if (Config.getUser().getGithubAuthToken() != null) {
            client.setOAuth2Token(Config.getUser().getGithubAuthToken());
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    public boolean login(String token) throws IOException {
        if (token != null){
            client.setOAuth2Token(token);
            isLoggedIn = true;
            System.out.println("Token is set.");
            return true;
        }
        return false;
    }

    public void logout(){
        client = new GitHubClient();
        userService = new UserService(client);
        isLoggedIn = false;
    }

    public void getAllRepos() throws IOException{
        RepositoryService repos = new RepositoryService(client);
        for (Repository repo : repos.getRepositories()){
            GitRepository gitRepo = new GitRepository(repo);
            this.repositorys.add(gitRepo);
        }
    }

    public void getCommits(GitRepository GitRepository) throws IOException {
        commits.clear();
        commitService = new CommitService(client);
        for (RepositoryCommit com : commitService.getCommits(GitRepository.getRepository())){
            GitCommit commit = new GitCommit(com);
            commits.add(commit);
        }
    }

    public void getContents(git.GitRepository GitRepository) throws IOException {
        contents.clear();
        contentsService = new ContentsService(client);
        for (RepositoryContents con : contentsService.getContents(GitRepository.getRepository())){
            GitContents content = new GitContents(con);
            contents.add(content);
        }
    }

    public void getContents(git.GitRepository gitRepository, String path) throws IOException {
        contents.clear();
        contentsService = new ContentsService(client);
        for (RepositoryContents con : contentsService.getContents(gitRepository.getRepository(),path)){
            GitContents content = new GitContents(con);
            contents.add(content);
        }
    }

    public void setAuthToken(String token){
        Config.getUser().setGithubAuthToken(token);
    }

}
