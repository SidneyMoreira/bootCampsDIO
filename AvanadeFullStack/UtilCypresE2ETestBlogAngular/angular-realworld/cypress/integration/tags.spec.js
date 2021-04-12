describe("Tags", () => {
  beforeEach(() => {
    cy.login("kadarpegasus@gmail.com", "1234@vai@");
  });
  it("Adicionar", () => {
    cy.contains("kadarpegasus").click();
    cy.location("pathname").should("contains", "/profile");
    cy.get(".article-preview").get("h1").first().click();
    cy.contains("Edit Article").click();
    cy.get("[placeholder='Enter tags']").type("dungeons{enter}");
    cy.get("[placeholder='Enter tags']").type("dragons{enter}");
    cy.contains("Publish Article").click();
    cy.get(".tag-list").contains("dragons");
  });
});
