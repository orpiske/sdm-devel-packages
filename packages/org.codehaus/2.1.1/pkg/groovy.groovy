
import net.orpiske.sdm.packages.BinaryPackage;


import static net.orpiske.sdm.lib.OsUtils.*;
import static net.orpiske.sdm.lib.io.IOUtil.*;
import static net.orpiske.sdm.lib.Core.*;
import static net.orpiske.sdm.lib.Executable.*;

class Groovy extends BinaryPackage {
	def version = "2.1.1"
	def name = "groovy"
	def url = "http://dist.groovy.codehaus.org/distributions/${name}-binary-${version}.zip"

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