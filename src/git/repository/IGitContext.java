package git.repository;

public interface IGitContext {

    boolean AddGitToken(String token);
    void RemoveGitToken();
    boolean EditMainRepository(String repository);
}
