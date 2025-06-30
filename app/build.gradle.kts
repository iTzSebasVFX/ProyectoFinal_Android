plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Plugins en la app
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.proyectofinal_recetas"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.proyectofinal_recetas"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // --- Retrofit y Moshi (Consumo de APIs REST y serialización JSON) ---

    implementation(libs.retrofit) // Cliente HTTP para consumir APIs REST.
    implementation(libs.retrofit.converter.moshi) // Convierte JSON usando Moshi en Retrofit.
    implementation(libs.moshi) // Librería para serializar/deserializar JSON en objetos Kotlin.
    ksp(libs.moshi.ksp) // Procesador de anotaciones para generar adaptadores de Moshi automáticamente.

    // --- Hilt (Inyección de dependencias simplificada) ---

    implementation(libs.hilt) // Biblioteca principal de Hilt para la inyección de dependencias.
    implementation(libs.hilt.navigation.compose) // Integración de Hilt con Jetpack Compose.
    ksp(libs.hilt.compiler) // Procesador que genera el código necesario para la DI con Hilt.

    // --- Lifecycle (Ciclo de vida y ViewModel) ---

    implementation(libs.lifecycle.viewmodel.ktx) // ViewModel con soporte para corrutinas y funciones extendidas de Kotlin.
    implementation(libs.lifecycle.viewmodel.compose) // Permite usar ViewModel directamente en composables.

    // --- Coroutines (Concurrencia asíncrona en Kotlin) ---

    implementation(libs.coroutines.core) // Biblioteca base de coroutines para código asincrónico en Kotlin.
    implementation(libs.kotlinx.coroutines.android) // Dispatcher y utilidades para usar coroutines en el hilo principal de Android.

    // --- Room (Persistencia de datos en base de datos local SQLite) ---

    implementation(libs.room.runtime) // Implementación principal de Room para acceso a BD.
    implementation(libs.room.ktx) // Extensiones de Kotlin y soporte para coroutines en Room.
    ksp(libs.room.compiler) // Procesador de anotaciones que genera DAOs y código SQL en tiempo de compilación.

    // --- Navigation (Navegación entre pantallas con Jetpack Compose) ---

    implementation(libs.navigation.compose) // Composable `NavHost`, `composable`, y manejo de rutas entre pantallas

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}