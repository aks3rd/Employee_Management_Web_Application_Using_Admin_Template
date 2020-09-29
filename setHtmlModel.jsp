<jsp:include page='/MasterPageTopSection.jsp' />
<script type="text/javascript">


function test()
{
 this.xyz=250;
}
var obj=new test();
function updateValue(val)
{
 obj.xyz=val;
}

function pageOnLoad()
{
 $$$.setHtmlModel(obj); 
}
window.addEventListener("load",pageOnLoad);

</script>

          <!-- Page Heading -->
<div>
         <h1 class="h3 mb-2 text-gray-800">Assignment  {{{   xyz   }}}</h1>
         
         <p>{{{xyz}}}</p>


	<h2>{{{xyz}}}</h2>
         <button onclick="updateValue(85)">85</button><button onclick="updateValue(450)">450</button><button onclick="updateValue(76)">76</button><button onclick="updateValue(70)">70</button>
<br><br>
         <h1 class="h3 mb-2 text-gray-800">Assignment {{{ x }}} in loop </h1>
         <p tmf-for="y=1 to 10 step 2">
		Hello {{{y}}}
         </p>

         <h1 class="h3 mb-2 text-gray-800">Assignment {{{ x }}} in nested loop </h1>
         <div tmf-for="x=1 to 10 step 2" >
          <p tmf-for="y=1 to 5">
	    Hello {{{y}}}
          </p>
         </div>
<br><br>

</div>

<jsp:include page='/MasterPageBottomSection.jsp' />
