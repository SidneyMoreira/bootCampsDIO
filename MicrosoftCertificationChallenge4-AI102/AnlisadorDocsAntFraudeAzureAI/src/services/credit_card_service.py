from azure.core.credentials import AzureKeyCredential
from azure.ai.documentintelligence import DocumentIntelligenceClient    
from azure.ai.documentintelligence.models import AnalyzeDocumentRequest
from utils.Config import Config

def analyze_credit_card(card_url):
    try:
      credential = AzureKeyCredential(Config.get_secret("AZURE_FORM_RECOGNIZER_KEY"))

      document_client = DocumentIntelligenceClient(Config.ENDPOINT, credential=credential)

      cad_info = document_client.begin_analyze_document(
          "prebuilt-creditCard", AnalyzeDocumentRequest(url_source=card_url))
      result = cad_info()

      for doc in result.documents:
          fields = doc.get("fields", {})
          card_info = {
              "card_name": fields.get("CardholderName",{}).get('content'),
              "bank_name": fields.get("Issuer",{}).get('content'),
              "expiry_date": fields.get("ExpirationDate",{}).get('content'),
              "card_number": fields.get("CardNumber",{}).get('content')
          }
          return card_info
    except Exception as e:
      print(f"Erro ao criar o cliente do Document Intelligence: {e}")
      return None