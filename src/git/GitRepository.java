package git;

import org.eclipse.egit.github.core.Repository;

public class GitRepository {
    Repository repository;

    public GitRepository(Repository repository) {
        this.repository = repository;
    }

    public Repository getRepository() {
        return repository;
    }

    @Override
    public String toString() {
        boolean isPrivate = this.repository.isPrivate();
        String repoStatus;
        if (isPrivate){
            repoStatus = "Private";
        }
        else  {
            repoStatus = "Public";
        }
        if (repository.getLanguage() != null){
            return repoStatus + " - " + this.repository.getName() + " - " + repository.getLanguage();
        }
        else   {
            return repoStatus + " - " + this.repository.getName() + " - " + "Unknown";
        }
    }
}
