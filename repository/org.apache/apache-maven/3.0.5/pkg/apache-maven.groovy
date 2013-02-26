import net.orpiske.sdm.packages.BinaryPackage;

class ApacheMaven extends BinaryPackage {
	def version = "3.0.5"
	def name = "apache-maven"
	
	def url = "http://apache.mirror.pop-sc.rnp.br/apache/maven/maven-3/${version}/binaries/apache-maven-${version}-bin.tar.gz"

	@Override
	void fetch(String url) {
		super.fetch(url)
		
		println "Fetching ${url}"	
	}
	
	@Override
	void verify(String artifactName) {
		println "Verifying ${artifactName}"
	}
	
	
	void install(String artifactName) {
		println "Installing ${artifactName}"
	}	

	
	
}