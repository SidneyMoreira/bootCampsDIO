//
//  ViewControllerTests.swift
//  XCTestPOCTests
//
//  Created by Roberth Diorges on 21/10/21.
//

import XCTest
@testable import XCTestPOC

class ViewControllerTests: XCTestCase {

    func testGenerateRandonNumberSucess() {
        
        let viewController = ViewController()
        viewController.generateEvenNumber()
        
        let randonNumber = viewController.generateRandonNumber()
        
        XCTAssertNotNil(randonNumber)
        XCTAssertNotEqual(randonNumber, "", "O retorno não pode ser vazio")
        
    }
    
    func testGenerateRandonNumberFailure() {
        
        let viewController = ViewController()
        
        let randonNumber = viewController.generateRandonNumber()
        XCTAssertEqual(randonNumber, "", "O retorno deve ser vazio")
        
    }
    
    func testGenerateRandonColor() {
        let viewController = ViewController()
        XCTAssertNotNil(viewController.generateRandonColor())
    }
    
    func testGenerateEvenNumber() {
        let viewController = ViewController()
        viewController.generateEvenNumber()
        
        guard let evenNumber = viewController.evenNumbers.first else {
            XCTFail()
            return
        }
        
        XCTAssertTrue(evenNumber % 2 == 0)
        
        
    }

    func testPerformanceExample() {
        self.measure {
            let viewController = ViewController()
            viewController.generateEvenNumber()
        }
    }
}
