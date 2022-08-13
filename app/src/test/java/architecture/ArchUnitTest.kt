package architecture

//internal class ArchUnitTest {
//
//    private val appPackage = "com.mateuszcholyn.wallet"
//
//    private val backendImplPackage = "$appPackage.backend.impl"
//
//    private val backendDomainPackage = "$backendImplPackage.domain.."
//    private val backendInfrastructurePackage = "$backendImplPackage.infrastructure.."
//    private val backendDiPackage = "$backendImplPackage.di.."
//
//    private val sourceClasses =
//        ClassFileImporter()
//            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
//            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
//            .importPackages(appPackage)
//
//    @Test
//    fun `backend domain model should not know about external layers`() {
//        // given
//        val backendDomainDontKnowAboutExternalLayersRule =
//            noClasses()
//                .that()
//                .resideInAPackage(backendDomainPackage)
//                .should()
//                .dependOnClassesThat()
//                .resideInAnyPackage(backendDiPackage, backendInfrastructurePackage)
//
//        // expect
//        backendDomainDontKnowAboutExternalLayersRule.check(sourceClasses)
//    }
//}