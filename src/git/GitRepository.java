package git;

import org.eclipse.egit.github.core.Repository;

public class GitRepository {
    private Repository repository;
    private String repoStatus;

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

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
