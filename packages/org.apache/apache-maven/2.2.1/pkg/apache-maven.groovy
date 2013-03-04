
import net.orpiske.ssps.common.repository.utils.InstallDirUtils;
import net.orpiske.sdm.common.WorkdirUtils;
import net.orpiske.sdm.lib.io.IOUtil;
import net.orpiske.sdm.packages.BinaryPackage;


import static net.orpiske.sdm.lib.OsUtils.*;
import static net.orpiske.sdm.lib.io.IOUtil.*;
import static net.orpiske.sdm.lib.Core.*;
import static net.orpiske.sdm.lib.Executable.*;

class ApacheMaven extends BinaryPackage {
	def version = "2.2.1"
	def name = "apache-maven"

	def url = "http://apache.mirror.pop-sc.rnp.br/apache/maven/maven-2/${version}/binaries/apache-maven-${version}-bin.tar.gz"
	
	void install(String artifactName) {
		shield("${installDir}/${name}-${version}/conf/settings.xml")		
		
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