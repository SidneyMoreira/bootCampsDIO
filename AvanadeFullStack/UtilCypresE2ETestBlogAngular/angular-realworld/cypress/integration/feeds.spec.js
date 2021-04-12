describe("Conduit Feed", () => {
  it("Ver Feeeds", () => {
    cy.login("kadarpegasus@gmail.com", "1234@vai@");
    cy.get(".nav-pills > .nav-item:nth-child(1) > .nav-link").click();
    cy.get(".nav-pills > .nav-item:nth-child(2) > .nav-link").click();
    cy.get("app-article-preview:nth-child(1) .btn").click();
  });
});
