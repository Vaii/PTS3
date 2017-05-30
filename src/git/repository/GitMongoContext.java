package git.repository;

import domain.Config;
import domain.DataSource;
import org.jongo.MongoCollection;

public class GitMongoContext implements IGitContext {

    @Override
    public boolean AddGitToken(String token) {
        try{
            Config.getUser().setGithubAuthToken(token);
            MongoCollection users = DataSource.connect().getCollection("Users");
            users.save(Config.getUser());
            System.out.println("Token saved.");
            return true;
        }
        catch (Exception ex){
            System.out.println("Token save failed.");
            return false;
        }

    }

    @Override
    public void RemoveGitToken() {
        Config.getUser().setGithubAuthToken(null);
        MongoCollection users = DataSource.connect().getCollection("Users");
        users.save(Config.getUser());
    }

    @Override
    public boolean EditMainRepository(String repository) {
        Config.getUser().setMainRepository(repository);
        MongoCollection users = DataSource.connect().getCollection("Users");
        users.save(Config.getUser());
        return true;
    }
}
