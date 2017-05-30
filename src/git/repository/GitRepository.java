package git.repository;

public class GitRepository {
    private IGitContext gitContext;

    public GitRepository(IGitContext gitContext) {
        this.gitContext = gitContext;
    }
    public boolean AddGitToken(String token){
        return gitContext.AddGitToken(token);
    }
    public void RemoveGitToken(){
        gitContext.RemoveGitToken();
    }
    public boolean EditMainRepository(String repository){
        return gitContext.EditMainRepository(repository);
    }
}
