package git;

import org.eclipse.egit.github.core.RepositoryCommit;

import java.io.Serializable;

public class GitCommit implements Serializable {
    private RepositoryCommit commit;

    public GitCommit(RepositoryCommit commit) {
        this.commit = commit;
    }

    @Override
    public String toString() {
        return this.commit.getCommit().getCommitter().getName() + " - " + this.commit.getCommit().getMessage();
    }


}

