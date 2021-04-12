describe("Logout", () => {
  it("Logout Via Perfil", () => {
    cy.login("kadarpegasus@gmail.com", "1234@vai@");
    cy.contains("Settings").click();
    cy.url().should("include", "/settings");
    cy.get(".btn-outline-danger").click();
  });
});
