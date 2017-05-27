package git;

import org.eclipse.egit.github.core.RepositoryContents;

/**
 * Created by Ken on 20-5-2017.
 */
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
