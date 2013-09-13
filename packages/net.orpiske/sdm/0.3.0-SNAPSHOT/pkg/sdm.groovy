import net.orpiske.sdm.packages.BinaryPackage
import net.orpiske.sdm.plugins.scm.SourceRepository
import net.orpiske.sdm.plugins.builders.ProjectBuilder;
import net.orpiske.sdm.plugins.builders.MavenBuilder;
import org.apache.commons.io.FileUtils;

import static net.orpiske.sdm.lib.OsUtils.*
import static net.orpiske.sdm.lib.Core.*;
import static net.orpiske.sdm.lib.Executable.*;

class sdm extends BinaryPackage {
    def version = "0.3.0-SNAPSHOT"
    def name = "sdm"
    def url = ""
    def srcUrl = "https://github.com/orpiske/sdm.git"
    

    @Override
    def void build() {
        File buildDir = new File("${workDir}/${name}-${version}-build")
        
        if (buildDir.exists()) {
            println "Cleaning up previously created directory"
            FileUtils.deleteDirectory(buildDir)
        }
        
        println "Checking out the code to " + buildDir.getPath();
        SourceRepository repository = new SourceRepository()
        repository.checkout(srcUrl, buildDir.getPath())
       
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