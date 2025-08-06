plugins {
  kotlin("multiplatform") version "2.2.0"
}

group = "cymru.asheiou"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

kotlin {
  mingwX64("native") {
    binaries {
      executable {
        entryPoint = "cymru.asheiou.numberparser.main"
      }
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(kotlin("stdlib-common"))
      }
    }
  }
}
