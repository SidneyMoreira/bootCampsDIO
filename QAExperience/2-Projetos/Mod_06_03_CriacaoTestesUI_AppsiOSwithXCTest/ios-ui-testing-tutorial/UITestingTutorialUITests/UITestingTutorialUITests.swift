//
//  UITestingTutorialUITests.swift
//  UITestingTutorialUITests
//
//  Created by Sidnei Moreira on 20/06/2023.
//  Copyright © 2023 Code Pro. All rights reserved.
//

import XCTest

class UITestingTutorialUITests: XCTestCase {
    let app = XCUIApplication()
    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.

        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false

        // UI tests must launch the application that they test. Doing this in setup will make sure it happens for each test method.
        //XCUIApplication().launch()
        
        app.launch()
        
        app.navigationBars["Mockify Music"].buttons["Profile"].tap()
        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    
    func testSuccessLogin(){
        
        let username = "CodePro"
        let password = "abc123"
        
        let usernameTextField = app.textFields["Username"]
        let passwordTextField = app.secureTextFields["Password"]
        
        usernameTextField.tap()
        usernameTextField.typeText(username)
        
        
        passwordTextField.tap()
        passwordTextField.typeText(password)
        XCTAssertTrue(usernameTextField.exists)
        XCTAssertTrue(passwordTextField.exists)
        
        let logar = app.buttons["Login"]
        logar.tap()
        
        let title = XCUIApplication().navigationBars["Mockify Music"]
        
        
        XCTAssertTrue(title.exists)
        
    }
    
    func testEmptyCredentials(){
        
        
        let login = app.buttons["Login"]
        login.tap()
        
        let missingCredentialsAlert = app.alerts["Missing Credentials"].scrollViews.otherElements.buttons["Ok"]
        XCTAssertNotNil(missingCredentialsAlert)
    }
    
    func testLoadingSpinnerStopped(){
        let login = app.buttons["Login"]
        login.tap()
        
        let alert = app.alerts["Missing Credentials"].buttons["Ok"]
        alert.tap()
      
        let spinnerProgress = app.activityIndicators["In progress"]
        
        
        XCTAssertFalse(spinnerProgress.exists)
    }
    
    func testWrongCredetials(){
        
        let username = "tesets"
        let password = "134124"
        
        
        let usernameTextField = app.textFields["Username"]
        let passwordTextField = app.secureTextFields["Password"]
        
        
        app.textFields["Username"].tap()
        usernameTextField.typeText(username)
        
        passwordTextField.tap()
        passwordTextField.typeText(password)
        
        app.buttons["Login"].tap()
        let alert = app.alerts["Invalid Credentials"].buttons["Ok"]
        alert.tap()
        
        XCTAssertTrue(usernameTextField.exists)
        XCTAssertTrue(passwordTextField.exists)
        XCTAssertNotNil(alert)
               
    }

}
