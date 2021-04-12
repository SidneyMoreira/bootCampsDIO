describe("Conduit Cadastro", () => {
  const usuario = "usuario" + new Date().getTime();
  const senha = "senha" + new Date().getTime();
  /* const usuario = "kadarpegasus@gmail.com";
  const senha = "1234@Vai@"; */
  it("Novo usuario", () => {
    cy.visit("/register");
    cy.get("[formcontrolname=username]").type(usuario);
    cy.get("[formcontrolname=email").type(usuario + "@algumacoisa.com");
    cy.get("[formcontrolname=password]").type(senha);
    cy.get(".btn").click();
    cy.contains(".nav-item:nth-child(4) > .nav-link", usuario).should(
      "be.visible"
    );
  });
  it("Usuario já existe!!", () => {
    cy.visit("/register");
    cy.get("[formcontrolname=username]").type(usuario);
    cy.get("[formcontrolname=email").type(usuario + "@algumacoisa.com");
    cy.get("[formcontrolname=password]").type(senha);
    cy.get(".btn").click();
    cy.location("pathname").should("equal", "/register");
    cy.get(".error-messages > li:nth-child(1)").should(
      "not.be.empty"
    );
  });
});
