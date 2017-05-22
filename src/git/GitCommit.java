package git;

import org.eclipse.egit.github.core.RepositoryCommit;

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

