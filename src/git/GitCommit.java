package git;

import org.eclipse.egit.github.core.RepositoryCommit;

/**
 * Created by Ken on 3-4-2017.
 */
public class GitCommit {
    RepositoryCommit commit;

    public GitCommit(RepositoryCommit commit) {
        this.commit = commit;
    }

    @Override
    public String toString() {
        return this.commit.getCommit().getCommitter().getName() + " - " + this.commit.getCommit().getMessage();
    }


}
