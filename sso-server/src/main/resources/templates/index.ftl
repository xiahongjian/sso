<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Index</title>
</head>
<body>
    <h1>This is index.</h1>
    <#if (Session.token)??>
    <a href="/logout">Logout</a>
    <#else>
    <a href="/login">Login</a>
    </#if>
    
</body>
</html>