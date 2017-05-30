package git.repository;

import domain.User;

public class GitRepository {
    private IGitContext gitContext;

    public GitRepository(IGitContext gitContext) {
        this.gitContext = gitContext;
    }
    public User AddGitToken(User user, String token){
        return gitContext.AddGitToken(user,token);
    }
    public User RemoveGitToken(User user){
        return gitContext.RemoveGitToken(user);
    }
    public User EditMainRepository(User user, String repository){
        return gitContext.EditMainRepository(user,repository);
    }
}
