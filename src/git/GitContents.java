package git;

import org.eclipse.egit.github.core.RepositoryContents;

public class GitContents {
    private RepositoryContents contents;

    public RepositoryContents getContents() {
        return contents;
    }

    public GitContents(RepositoryContents contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return this.contents.getType() + " - " + this.contents.getName() ;
    }


}
