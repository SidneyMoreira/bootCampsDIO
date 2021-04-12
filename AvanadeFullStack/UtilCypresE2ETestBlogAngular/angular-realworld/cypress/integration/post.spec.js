describe("Post", () => {
  beforeEach(() => {
    cy.login("kadarpegasus@gmail.com", "1234@vai@");
  });
  it("Novo", () => {
    const tit = "Cypress Hill E2E";
    cy.contains("New Article").click();
    cy.location("pathname").should("equal", "/editor");
    cy.get("[formcontrolname=title]").type(tit);
    cy.get("[formcontrolname=description]").type("Teste de ponta a ponta");
    cy.get("[formcontrolname=body]").type(
      "Agilidade, Qualidade, Aprendendo a testar"
    );
    cy.contains("Publish Article").click();
    cy.get("h1").contains(tit);
  });
  it("Editar", () => {
    cy.contains("kadarpegasus").click();
    cy.location("pathname").should("contains", "/profile");
    cy.get(".article-preview").get("h1").first().click();
    cy.contains("Edit Article").click();
    cy.get("[formcontrolname=body]").clear();
    cy.get("[formcontrolname=body]").type(
      "Facilidade para testar a edição/alteração"
    );
    cy.contains("Publish Article").click();
    cy.contains("Facilidade para testar a edição/alteração");
  });
});
