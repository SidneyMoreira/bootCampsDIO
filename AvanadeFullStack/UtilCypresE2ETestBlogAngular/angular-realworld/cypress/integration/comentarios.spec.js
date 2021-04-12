describe("Comentários", () => {
  beforeEach(() => {
    cy.login("kadarpegasus@gmail.com", "1234@vai@");
  });
  it("Escrever", () => {
    cy.contains("Global Feed").click();
    cy.get(".article-preview").first().click();
    cy.get(".form-control").type("Expetacular!");
    cy.get(".btn-primary").click();
    cy.contains("Expetacular!");
  });
});
