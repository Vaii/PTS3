package git.repository;

import domain.User;

public interface IGitContext {

    User AddGitToken(User user, String token);
    User RemoveGitToken(User user);
    User EditMainRepository(User user, String repository);
}
