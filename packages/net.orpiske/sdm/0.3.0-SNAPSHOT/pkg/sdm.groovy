import net.orpiske.sdm.packages.SourcePackage
import net.orpiske.sdm.plugins.scm.SourceRepository
import net.orpiske.sdm.plugins.builders.ProjectBuilder;
import net.orpiske.sdm.plugins.builders.MavenBuilder;
import org.apache.commons.io.FileUtils;

import static net.orpiske.sdm.lib.OsUtils.*
import static net.orpiske.sdm.lib.Core.*;
import static net.orpiske.sdm.lib.Executable.*
import static net.orpiske.sdm.lib.Unpack.unpack;

class sdm extends SourcePackage {
    def version = "0.3.0-SNAPSHOT"
    def name = "sdm"
    def url = "https://github.com/orpiske/sdm.git"

	def dependencies = [ "net.orpiske/ssps-common":"(0.3.0-SNAPSHOT, 0.3.99)",
						"net.orpiske/sdm-common":"(0.3.0-SNAPSHOT, 0.3.99)"]
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
    }

    void install() {
		unpack(buildDir.getPath() + File.separator + "target" + File.separator +
				"${name}-${version}-bin.tar.gz", installDir)

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
