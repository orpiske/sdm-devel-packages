
import net.orpiske.ssps.common.repository.utils.InstallDirUtils;
import net.orpiske.sdm.common.WorkdirUtils;
import net.orpiske.sdm.lib.io.IOUtil;
import net.orpiske.sdm.packages.BinaryPackage;

import static net.orpiske.sdm.lib.net.Downloader.*;
import static net.orpiske.sdm.lib.Unpack.*;
import static net.orpiske.sdm.lib.OsUtils.*;
import static net.orpiske.sdm.lib.io.IOUtil.*;
import static net.orpiske.sdm.lib.Core.*;
import static net.orpiske.sdm.lib.Executable.*;

class ApacheMaven extends BinaryPackage {
	def version = "3.0.5"
	def name = "apache-maven"
	
	def url = "http://apache.mirror.pop-sc.rnp.br/apache/maven/maven-3/${version}/binaries/apache-maven-${version}-bin.tar.gz"

	void fetch(String url) {
		super.fetch(url)
	
		
		println "Fetching ${url}"
		download(url)		
	}
	
	
	void extract(String artifactName) {
		unpack(artifactName)
	}
	
	
	void install(String artifactName) {
		String installdir = InstallDirUtils.getInstallDir()
		String workdir = WorkdirUtils.getWorkDir()
		
		shield("${installdir}/${name}-${version}/conf/settings.xml")
		//IOUtil.copy("${workdir}/${name}-${version}", "${installdir}/${name}-${version}");
		
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