# SendGrid_Proxy
This repository contains workflow which helps in capturing the SendGrid implementation including capturing the details of an email in database along with capturing SendGrid server side fail over scenarios.

# Description:
SendGrid email API will capture all the server side bounce backs/ failures with in sendgrid, but applications must fetch bounce backs/ failures and create a report accordingly. To achieve this, we need to set up a proxy layer between our application and SendGrid to fetch bounce backs/ failures.  

Technical design details are captured in SendGrid Proxy Workflow Design Document "SendGrid Proxy Workflow Design Document.docx"
