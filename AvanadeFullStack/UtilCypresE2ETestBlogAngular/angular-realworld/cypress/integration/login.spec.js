
describe("Conduit Login", () => {
  it("Login efetuado com sucesso!!", () => {
    cy.login("kadarpegasus@gmail.com", "1234@vai@");
    cy.get(".nav-item:nth-child(4) > .nav-link").click();
    cy.get(".btn:nth-child(5)").click();
    cy.url().should("contain", "/settings");
  });
  it('Dados inválidado', () =>{
    cy.login("usuario@inexistente.com", "senhaerrada");
    cy.get(".error-messages > li").should(
      "contain", "email or password is invalid"
    );
  })
});
