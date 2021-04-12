describe("Profile", () => {
  it("Editar Profile", () => {
    cy.login("kadarpegasus@gmail.com", "1234@vai@");
    cy.contains("kadarpegasus").click();
    cy.contains("Edit Profile Settings").click();
    cy.get("[formcontrolname=image").clear();
    cy.get("[formcontrolname=image").type(
      "https://thispersondoesnotexist.com/image"
    );
    cy.get("[formcontrolname=password").type("1234@vai@");
    cy.contains("Update Settings").click();
  });
});
