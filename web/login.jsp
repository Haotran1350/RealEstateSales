<%-- 
    Document   : login
    Created on : Feb 6, 2026, 10:14:42 AM
    Author     : Hao
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>

    <style>
        :root{
            /* LIGHT THEME */
            --bg1:#f8fafc;       /* slate-50 */
            --bg2:#eef2ff;       /* indigo-50 */
            --card:#ffffffcc;    /* glass */
            --stroke:#e5e7eb;    /* gray-200 */
            --text:#0f172a;      /* slate-900 */
            --muted:#475569;     /* slate-600 */
            --accent:#2563eb;    /* blue-600 */
            --accent2:#7c3aed;   /* violet-600 */
            --danger:#e11d48;    /* rose-600 */
            --shadow: 0 18px 60px rgba(2,6,23,.12);
        }

        *{ box-sizing:border-box; }
        body{
            margin:0;
            min-height:100vh;
            display:flex;
            align-items:center;
            justify-content:center;
            font-family: system-ui, -apple-system, Segoe UI, Roboto, Arial, sans-serif;
            color:var(--text);
            background:
                radial-gradient(900px 500px at 15% 15%, rgba(37,99,235,.14), transparent 55%),
                radial-gradient(900px 500px at 85% 85%, rgba(124,58,237,.12), transparent 55%),
                linear-gradient(160deg, var(--bg1), var(--bg2));
        }

        .card{
            width:min(420px, 92vw);
            background: linear-gradient(180deg, rgba(255,255,255,.95), rgba(255,255,255,.85));
            border:1px solid rgba(15,23,42,.08);
            box-shadow: var(--shadow);
            border-radius: 18px;
            padding: 26px 24px;
            backdrop-filter: blur(10px);
            animation: pop .45s ease-out;
        }

        @keyframes pop{
            from{ transform: translateY(10px); opacity:.0; }
            to{ transform: translateY(0); opacity:1; }
        }

        .title{
            margin:0 0 6px 0;
            font-size: 22px;
            letter-spacing:.2px;
        }
        .subtitle{
            margin:0 0 18px 0;
            color:rgba(71,85,105,.9);
            font-size: 13.5px;
            line-height:1.4;
        }

        .error{
            margin: 0 0 14px 0;
            padding: 10px 12px;
            border-radius: 12px;
            background: rgba(225,29,72,.08);
            border: 1px solid rgba(225,29,72,.18);
            color: #9f1239;
            font-size: 13.5px;
        }

        .field{
            display:flex;
            flex-direction:column;
            gap:8px;
            margin-bottom: 12px;
        }

        label{
            font-size: 13px;
            color: rgba(71,85,105,.95);
        }

        .input{
            width:100%;
            padding: 12px 12px;
            border-radius: 12px;
            border: 1px solid rgba(15,23,42,.10);
            background: rgba(255,255,255,.85);
            color: var(--text);
            outline: none;
            transition: transform .12s ease, border-color .2s ease, box-shadow .2s ease, background .2s ease;
        }
        .input::placeholder{ color: rgba(71,85,105,.55); }
        .input:hover{
            border-color: rgba(37,99,235,.35);
        }
        .input:focus{
            border-color: rgba(37,99,235,.65);
            box-shadow: 0 0 0 4px rgba(37,99,235,.14);
            transform: translateY(-1px);
            background: #fff;
        }

        /* password row with eye button */
        .pw-wrap{
            position: relative;
        }
        .pw-wrap .input{
            padding-right: 44px; /* chừa chỗ cho icon mắt */
        }
        .eye-btn{
            position:absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            width: 34px;
            height: 34px;
            border-radius: 10px;
            border: 1px solid rgba(15,23,42,.10);
            background: rgba(248,250,252,.9);
            cursor:pointer;
            display:flex;
            align-items:center;
            justify-content:center;
            transition: transform .12s ease, box-shadow .2s ease, border-color .2s ease, background .2s ease;
        }
        .eye-btn:hover{
            border-color: rgba(37,99,235,.35);
            box-shadow: 0 10px 22px rgba(2,6,23,.10);
            transform: translateY(-50%) scale(1.03);
            background: #fff;
        }
        .eye-btn:active{
            transform: translateY(-50%) scale(.98);
        }
        .eye-btn:focus{
            outline:none;
            box-shadow: 0 0 0 4px rgba(124,58,237,.14), 0 10px 22px rgba(2,6,23,.10);
        }
        .eye-btn svg{
            width:18px;
            height:18px;
            fill: none;
            stroke: rgba(15,23,42,.78);
            stroke-width: 2;
            stroke-linecap: round;
            stroke-linejoin: round;
        }

        .row{
            display:flex;
            align-items:center;
            justify-content:space-between;
            gap:12px;
            margin: 10px 0 6px;
        }
        .hint{
            font-size: 12.5px;
            color: rgba(71,85,105,.75);
        }

        .btn{
            width:100%;
            border: none;
            border-radius: 12px;
            padding: 12px 14px;
            font-weight: 700;
            letter-spacing:.2px;
            color: #ffffff;
            background: linear-gradient(90deg, var(--accent), var(--accent2));
            cursor: pointer;
            transition: transform .12s ease, filter .2s ease, box-shadow .2s ease;
            box-shadow: 0 14px 32px rgba(37,99,235,.20);
        }
        .btn:hover{
            filter: brightness(1.03);
            transform: translateY(-1px);
            box-shadow: 0 18px 40px rgba(37,99,235,.24);
        }
        .btn:active{
            transform: translateY(0px) scale(.99);
        }
        .btn:focus{
            outline: none;
            box-shadow: 0 0 0 4px rgba(124,58,237,.16), 0 18px 40px rgba(37,99,235,.24);
        }

        input { caret-color: var(--accent); }

        .footer{
            margin-top: 14px;
            font-size: 12px;
            color: rgba(71,85,105,.65);
            text-align:center;
        }
    </style>
</head>

<body>

    <div class="card">
        <h2 class="title">Login</h2>
        <p class="subtitle">Đăng nhập để vào hệ thống.</p>

        <c:if test="${not empty requestScope.error}">
            <div class="error">${requestScope.error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="login"/>

            <div class="field">
                <label for="username">Username</label>
                <input id="username" class="input" type="text" name="username" required
                       placeholder="Nhập username..." autocomplete="username" />
            </div>

            <div class="field">
                <label for="password">Password</label>

                <div class="pw-wrap">
                    <input id="password" class="input" type="password" name="password" required
                           placeholder="Nhập password..." autocomplete="current-password" />

                    <button class="eye-btn" type="button" id="togglePw" aria-label="Show password">
                        <!-- eye icon -->
                        <svg id="eyeOpen" viewBox="0 0 24 24" aria-hidden="true">
                            <path d="M2 12s3.5-7 10-7 10 7 10 7-3.5 7-10 7S2 12 2 12z"></path>
                            <circle cx="12" cy="12" r="3"></circle>
                        </svg>

                        <!-- eye-off icon (hidden by default) -->
                        <svg id="eyeOff" viewBox="0 0 24 24" aria-hidden="true" style="display:none;">
                            <path d="M3 3l18 18"></path>
                            <path d="M10.6 10.6A3 3 0 0 0 12 15a3 3 0 0 0 2.4-4.4"></path>
                            <path d="M6.2 6.2C3.6 8.3 2 12 2 12s3.5 7 10 7c2 0 3.7-.5 5.1-1.3"></path>
                            <path d="M9.9 4.3A10.7 10.7 0 0 1 12 5c6.5 0 10 7 10 7a17.5 17.5 0 0 1-3.2 4.6"></path>
                        </svg>
                    </button>
                </div>
            </div>

           

            <button class="btn" type="submit">Login</button>

            <div class="footer">© Your App</div>
        </form>
    </div>

    <script>
        (function () {
            var pw = document.getElementById("password");
            var btn = document.getElementById("togglePw");
            var eyeOpen = document.getElementById("eyeOpen");
            var eyeOff = document.getElementById("eyeOff");

            btn.addEventListener("click", function () {
                var isHidden = (pw.type === "password");
                pw.type = isHidden ? "text" : "password";
                eyeOpen.style.display = isHidden ? "none" : "block";
                eyeOff.style.display = isHidden ? "block" : "none";
                btn.setAttribute("aria-label", isHidden ? "Hide password" : "Show password");
                pw.focus();
            });
        })();
    </script>

</body>
</html>
