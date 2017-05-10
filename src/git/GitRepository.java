package git;

import org.eclipse.egit.github.core.Repository;

/**
 * Created by Ken on 3-4-2017.
 */
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
            return this.repository.getName();
        }
    }
}
