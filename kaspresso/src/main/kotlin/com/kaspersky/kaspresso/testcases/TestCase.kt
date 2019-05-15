package com.kaspersky.kaspresso.testcases

import com.kaspersky.kaspresso.configurator.Configurator

/**
 *  A base class for all test cases. Extend this class with a single base project-wide inheritor of [TestCase] as a
 *  parent for all actual project-wide test cases. Nesting test cases are not permitted because they may produce an
 *  exception caused by re-initialization of the [Configurator], use [Scenario] instead.
 */
abstract class TestCase(
    configBuilder: Configurator.Builder = Configurator.Builder.default()
) {
    /**
     * Finishes building of [Configurator]. Passing [Configurator.Builder] to base [TestCase]'s constructor is the only
     * way for project-wide inheritor of [TestCase] to tune [Configurator].
     */
    init {
        configBuilder.commit()
    }

    /**
     * Starts the building a test, sets the [BeforeTestSection] actions and returns an existing instance of
     * [AfterTestSection] to continue building a test.
     *
     * @param actions actions to invoke in before test section.
     * @return an existing instance of [AfterTestSection].
     */
    protected fun beforeTest(actions: () -> Unit) = createBeforeTestSection().beforeTest(actions)

    /**
     * Creates an instance of [BeforeTestSection] with a new instance of [MainTestSection] as a parameter.
     */
    private fun createBeforeTestSection(): BeforeTestSection {
        return BeforeTestSection(
            TestBody
                .builder()
                .className(javaClass.simpleName)
        )
    }
}