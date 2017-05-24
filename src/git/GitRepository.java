package git;

import org.eclipse.egit.github.core.Repository;

public class GitRepository {
    Repository repository;
    String repoStatus;
    public GitRepository(Repository repository) {
        this.repository = repository;
        if (repository.isPrivate()){
            repoStatus = "Private";
        }
        else  {
            repoStatus = "Public";
        }
    }

    public Repository getRepository() {
        return repository;
    }

    @Override
    public String toString() {
        if (repository.getLanguage() != null){
            return this.repository.getName();
        }
        else   {
            return this.repository.getName();
        }
    }
}
