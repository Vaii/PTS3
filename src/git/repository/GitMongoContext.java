package git.repository;

import domain.User;

public class GitMongoContext implements IGitContext {

    @Override
    public User AddGitToken(User user, String token) {
        return null;
    }

    @Override
    public User RemoveGitToken(User user) {
        return null;
    }

    @Override
    public User EditMainRepository(User user, String Repository) {
        return null;
    }
}
