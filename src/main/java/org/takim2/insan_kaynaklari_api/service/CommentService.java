package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.Vw.CompanyView;
import org.takim2.insan_kaynaklari_api.dto.request.CommentSaveRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.CommentResponseDto;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeResponseDto;
import org.takim2.insan_kaynaklari_api.entity.Comment;
import org.takim2.insan_kaynaklari_api.entity.Company;
import org.takim2.insan_kaynaklari_api.entity.CompanyManager;
import org.takim2.insan_kaynaklari_api.entity.Employee;
import org.takim2.insan_kaynaklari_api.entity.enums.CommentStatus;
import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.repository.CommentRepository;
import org.takim2.insan_kaynaklari_api.repository.CompanyManagerRepository;
import org.takim2.insan_kaynaklari_api.repository.CompanyRepository;
import org.takim2.insan_kaynaklari_api.security.JwtTokenFilter;
import org.takim2.insan_kaynaklari_api.util.JwtTokenManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CompanyManagerRepository companyManagerRepository;
    private final CompanyRepository companyRepository;
    private final JwtTokenManager  jwtTokenManager;

    public void saveComment(CommentSaveRequestDto dto) {
        Long userId = jwtTokenManager.getUserIdFromToken(dto.getToken()).orElseThrow(() -> new HumanResourcesAppException(ErrorType.TOKEN_ARGUMENT_NOTVALID));
        Optional<CompanyManager> companyManager = companyManagerRepository.findByUserId(userId);
        if (companyManager.isEmpty()) {
            throw new HumanResourcesAppException(ErrorType.USER_NOT_FOUND);
        }
        Company company = companyRepository.findById(dto.getCompanyId()).orElseThrow(() -> new HumanResourcesAppException(ErrorType.COMPANY_NOT_FOUND));
        commentRepository.save(Comment
                .builder()
                .company(company)
                .companyManager(companyManager.get())
                .content(dto.getContent())
                .commentStatus(CommentStatus.APPROVED)
                .build());
    }

    public List<CommentResponseDto> getAllComment(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentPage = commentRepository.findAll(pageable);

        return commentPage.getContent().stream()
                .map(comment -> CommentResponseDto.builder()
                        .companyName(comment.getCompany().getCompanyName())
                        .companyManagerFirstName(comment.getCompanyManager().getUser().getFirstName())
                        .companyManagerLastName(comment.getCompanyManager().getUser().getLastName())
                        .companyManagerAvatar(comment.getCompanyManager().getUser().getAvatar())
                        .content(comment.getContent())
                        .build())
                .collect(Collectors.toList());
    }

}
