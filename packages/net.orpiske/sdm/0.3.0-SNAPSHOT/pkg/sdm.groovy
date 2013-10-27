import net.orpiske.sdm.packages.SourcePackage
import net.orpiske.sdm.plugins.scm.SourceRepository
import net.orpiske.sdm.plugins.builders.ProjectBuilder;
import net.orpiske.sdm.plugins.builders.MavenBuilder;
import org.apache.commons.io.FileUtils;

import static net.orpiske.sdm.lib.OsUtils.*
import static net.orpiske.sdm.lib.Core.*;
import static net.orpiske.sdm.lib.Executable.*;

class sdm extends SourcePackage {
    def version = "0.3.0-SNAPSHOT"
    def name = "sdm"
    def url = "https://github.com/orpiske/sdm.git"
       
    def File buildDir = new File("${workDir}/${name}-${version}-build")
        
    void fetch(final String url) {
        println "Checking out the code to " + buildDir.getPath();

        if (buildDir.exists()) {
            println "Cleaning up previously created directory"
            FileUtils.deleteDirectory(buildDir)
        }

        SourceRepository repository = new SourceRepository(null, null)
        repository.checkout(url, buildDir.getPath())
    }
    

    @Override
    void build() {
        ProjectBuilder builder = new MavenBuilder(buildDir)

        builder.setProfile("Delivery")
        if (builder.createPackage() == 0) {
            println "Project ${name} compiled successfully"
        }

        extract(buildDir.getPath() + File.separator + "target" + File.separator +
                "${name}-${version}-bin.tar.gz")
    }
    
    void install() {
        performInstall("${name}", "${version}")

        if (isNix()) {
            println "Creating symlinks"
            exec("/bin/ln", "-sf ${installDir}/${name}-${version} ${installDir}/${name}")
        }
    }

    void uninstall() {
        if (isNix()) {
            println "Removing symlinks"
            exec("/bin/unlink", "${installDir}/${name}")
        }
    }
}