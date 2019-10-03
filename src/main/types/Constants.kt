package types

object Constants {

    const val cmdPrefix = "diff2html --style side"

    fun cmdSuffix(checkedOutDir: String, nameOfOutFile: String, commitHash1: String, commitHash2: String): String {
        return "--file $checkedOutDir$nameOfOutFile.html -- -M $commitHash1 $commitHash2"
    }

    const val excludesAndroid: String = "--ig ./app/src/androidTestDebug/ --ig ./app/src/main/assets/ --ig ./app/src/main/res/ " +
            "--ig ./app/src/test/ --ig ./app/build.gradle --ig ./app/gradle.properties --ig .idea/codeStyles/ " +
            "--ig ./app/src/debug/assets/ --ig ./app/src/debug/google-services.json " +
            "--ig ./app/src/debug/AndroidManifest.xml --ig ./app/src/debug/res/ --ig .gitignore " +
            "--ig ./app/src/release/google-services.json --ig build.gradle --ig ./docs/ --ig gradle.properties " +
            "--ig ./scripts/ --ig ./gradle/wrapper/ --ig ./app/src/release/res/ --ig ./app/src/main/AndroidManifest.xml"

    const val excludesiOS: String = "--ig ./*/Xcconfigs --ig ./*.xcodeproj --ig ./*/config/ --ig ./Profiles/ --ig ./xcode_configs/"


}