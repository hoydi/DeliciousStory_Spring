function deleteBoard(id) {
    if (confirm('정말 삭제하시겠습니까?')) {
        fetch(`/boards/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                window.location.href = '/boards'; // 삭제 후 게시글 목록으로 리다이렉트
            } else {
                alert('게시글 삭제에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('삭제 요청 중 오류 발생:', error);
        });
    }
}