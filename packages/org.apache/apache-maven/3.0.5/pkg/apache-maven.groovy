
import net.orpiske.ssps.common.repository.utils.InstallDirUtils;
import net.orpiske.sdm.common.WorkdirUtils;
import net.orpiske.sdm.lib.io.IOUtil;
import net.orpiske.sdm.packages.BinaryPackage;


import static net.orpiske.sdm.lib.OsUtils.*;
import static net.orpiske.sdm.lib.io.IOUtil.*;
import static net.orpiske.sdm.lib.Core.*;
import static net.orpiske.sdm.lib.Executable.*;

class ApacheMaven extends BinaryPackage {
	def version = "3.0.5"
	def name = "apache-maven"
	def url = "http://apache.mirror.pop-sc.rnp.br/apache/maven/maven-3/${version}/binaries/apache-maven-${version}-bin.tar.gz"
	def installdir = InstallDirUtils.getInstallDir()

	
	void install(String artifactName) {		
		String workdir = WorkdirUtils.getWorkDir()

		shield("${installdir}/${name}-${version}/conf/settings.xml")		
		
		performInstall("${name}", "${version}")
		
		if (isNix()) {
			println "Creating symlinks"
			exec("/bin/ln", "-sf ${installdir}/${name}-${version} ${installdir}/${name}")
		}
	}
	
	void uninstall() {
		if (isNix()) {
			println "Removing symlinks"
			exec("/bin/unlink", "${installdir}/${name}")
		}
	}
}