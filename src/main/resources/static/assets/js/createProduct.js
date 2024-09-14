	function openCreateColorModal() {
 		   document.getElementById('modalOverlay').style.display = 'block';
  		   document.getElementById('createColorModal').style.display = 'block';
		}

		function openCreateSizeModal() {
  		   document.getElementById('modalOverlay').style.display = 'block';
    	   document.getElementById('createSizeModal').style.display = 'block';
		}

		function openCreateCategoryModal() {
	  		   document.getElementById('modalOverlay').style.display = 'block';
	    	   document.getElementById('createCategoryModal').style.display = 'block';
			}

		
		function closeModals() {
  		  document.getElementById('modalOverlay').style.display = 'none';
		  document.getElementById('createColorModal').style.display = 'none';
		  document.getElementById('createSizeModal').style.display = 'none';
		  document.getElementById('createCategoryModal').style.display = 'none';
		}